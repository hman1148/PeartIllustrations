{{- define "peart-illustrations.fullname" -}}
{{- printf "%s" .Release.Name -}}
{{- end -}}

{{- define "peart-illustrations.name" -}}
{{- printf "%s" .Chart.Name -}}
{{- end -}}